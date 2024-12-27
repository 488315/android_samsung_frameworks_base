package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.content.pm.PackageManager;
import android.os.Bundle;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class CustomTileRepositoryImpl$isTileToggleable$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CustomTileRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTileRepositoryImpl$isTileToggleable$2(CustomTileRepositoryImpl customTileRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customTileRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomTileRepositoryImpl$isTileToggleable$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomTileRepositoryImpl$isTileToggleable$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = false;
        try {
            CustomTileRepositoryImpl customTileRepositoryImpl = this.this$0;
            Bundle bundle = customTileRepositoryImpl.packageManagerAdapter.mPackageManager.getServiceInfo(customTileRepositoryImpl.tileSpec.componentName, 794752).metaData;
            if (bundle != null) {
                if (bundle.getBoolean("android.service.quicksettings.TOGGLEABLE_TILE", false)) {
                    z = true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return Boolean.valueOf(z);
    }
}
