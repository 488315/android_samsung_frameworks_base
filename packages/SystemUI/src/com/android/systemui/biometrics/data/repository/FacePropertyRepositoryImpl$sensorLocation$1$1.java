package com.android.systemui.biometrics.data.repository;

import android.graphics.Point;
import android.util.RotationUtils;
import android.util.Size;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import com.android.systemui.biometrics.shared.model.DisplayRotationKt$WhenMappings;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class FacePropertyRepositoryImpl$sensorLocation$1$1 extends SuspendLambda implements Function5 {
    /* synthetic */ float F$0;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;
    final /* synthetic */ FacePropertyRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FacePropertyRepositoryImpl$sensorLocation$1$1(FacePropertyRepositoryImpl facePropertyRepositoryImpl, Continuation continuation) {
        super(5, continuation);
        this.this$0 = facePropertyRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        float floatValue = ((Number) obj4).floatValue();
        FacePropertyRepositoryImpl$sensorLocation$1$1 facePropertyRepositoryImpl$sensorLocation$1$1 = new FacePropertyRepositoryImpl$sensorLocation$1$1(this.this$0, (Continuation) obj5);
        facePropertyRepositoryImpl$sensorLocation$1$1.L$0 = (Point) obj;
        facePropertyRepositoryImpl$sensorLocation$1$1.L$1 = (DisplayRotation) obj2;
        facePropertyRepositoryImpl$sensorLocation$1$1.L$2 = (Size) obj3;
        facePropertyRepositoryImpl$sensorLocation$1$1.F$0 = floatValue;
        return facePropertyRepositoryImpl$sensorLocation$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Point point = (Point) this.L$0;
        DisplayRotation displayRotation = (DisplayRotation) this.L$1;
        Size size = (Size) this.L$2;
        float f = this.F$0;
        this.this$0.getClass();
        if (point == null) {
            return null;
        }
        Point point2 = new Point((int) (point.x * f), (int) (point.y * f));
        int i = DisplayRotationKt$WhenMappings.$EnumSwitchMapping$0[displayRotation.ordinal()];
        int i2 = 1;
        if (i == 1) {
            i2 = 0;
        } else if (i != 2) {
            i2 = 3;
            if (i == 3) {
                i2 = 2;
            } else if (i != 4) {
                throw new NoWhenBranchMatchedException();
            }
        }
        RotationUtils.rotatePoint(point2, i2, size.getWidth(), size.getHeight());
        return point2;
    }
}
