package com.android.systemui.qs.tiles.impl.rotation.domain.interactor;

import android.R;
import com.android.systemui.qs.tiles.impl.rotation.domain.model.RotationLockTileModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class RotationLockTileDataInteractor$tileData$1 extends SuspendLambda implements Function5 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    /* synthetic */ boolean Z$3;
    int label;
    final /* synthetic */ RotationLockTileDataInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RotationLockTileDataInteractor$tileData$1(RotationLockTileDataInteractor rotationLockTileDataInteractor, Continuation continuation) {
        super(5, continuation);
        this.this$0 = rotationLockTileDataInteractor;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        boolean booleanValue4 = ((Boolean) obj4).booleanValue();
        RotationLockTileDataInteractor$tileData$1 rotationLockTileDataInteractor$tileData$1 = new RotationLockTileDataInteractor$tileData$1(this.this$0, (Continuation) obj5);
        rotationLockTileDataInteractor$tileData$1.Z$0 = booleanValue;
        rotationLockTileDataInteractor$tileData$1.Z$1 = booleanValue2;
        rotationLockTileDataInteractor$tileData$1.Z$2 = booleanValue3;
        rotationLockTileDataInteractor$tileData$1.Z$3 = booleanValue4;
        return rotationLockTileDataInteractor$tileData$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String rotationResolverPackageName;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = this.Z$2;
        boolean z4 = this.Z$3;
        RotationLockTileDataInteractor rotationLockTileDataInteractor = this.this$0;
        return new RotationLockTileModel(z, rotationLockTileDataInteractor.resources.getBoolean(R.bool.config_allowTheaterModeWakeFromKey) && !z3 && !z2 && (rotationResolverPackageName = rotationLockTileDataInteractor.packageManager.getRotationResolverPackageName()) != null && rotationLockTileDataInteractor.packageManager.checkPermission("android.permission.CAMERA", rotationResolverPackageName) == 0 && z4);
    }
}
