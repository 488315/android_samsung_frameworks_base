package com.android.systemui.util.icons;

import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class AppCategoryIconProviderImpl$getPackageInfo$4 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $packageName;
    int label;
    final /* synthetic */ AppCategoryIconProviderImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppCategoryIconProviderImpl$getPackageInfo$4(AppCategoryIconProviderImpl appCategoryIconProviderImpl, String str, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appCategoryIconProviderImpl;
        this.$packageName = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppCategoryIconProviderImpl$getPackageInfo$4(this.this$0, this.$packageName, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        PackageManager packageManager;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            packageManager = this.this$0.packageManager;
            return packageManager.getPackageInfo(this.$packageName, 0);
        } catch (RemoteException unused) {
            Log.e("DefaultAppsIconProvider", "Failed to retrieve package info for " + this.$packageName);
            return null;
        }
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((AppCategoryIconProviderImpl$getPackageInfo$4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
