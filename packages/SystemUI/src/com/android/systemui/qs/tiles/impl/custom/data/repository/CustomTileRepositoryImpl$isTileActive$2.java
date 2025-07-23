package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.content.ComponentName;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.systemui.qs.external.PackageManagerAdapter;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CustomTileRepositoryImpl$isTileActive$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CustomTileRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTileRepositoryImpl$isTileActive$2(CustomTileRepositoryImpl customTileRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customTileRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomTileRepositoryImpl$isTileActive$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomTileRepositoryImpl$isTileActive$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Bundle bundle;
        UserHandle userHandle;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = false;
        try {
            CustomTileRepositoryImpl customTileRepositoryImpl = this.this$0;
            PackageManagerAdapter packageManagerAdapter = customTileRepositoryImpl.packageManagerAdapter;
            ComponentName componentName = customTileRepositoryImpl.tileSpec.componentName;
            CustomTileRepositoryImpl.TileWithUser currentTileWithUser = customTileRepositoryImpl.getCurrentTileWithUser();
            ServiceInfo serviceInfo = packageManagerAdapter.mIPackageManager.getServiceInfo(componentName, 794752, (currentTileWithUser == null || (userHandle = currentTileWithUser.user) == null) ? -2 : userHandle.getIdentifier());
            if (serviceInfo != null && (bundle = serviceInfo.metaData) != null) {
                if (bundle.getBoolean("android.service.quicksettings.ACTIVE_TILE", false)) {
                    z = true;
                }
            }
        } catch (RemoteException unused) {
        }
        return Boolean.valueOf(z);
    }
}
