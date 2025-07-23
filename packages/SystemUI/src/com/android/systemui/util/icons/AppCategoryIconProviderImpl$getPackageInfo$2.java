package com.android.systemui.util.icons;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import com.android.systemui.shared.system.PackageManagerWrapper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class AppCategoryIconProviderImpl$getPackageInfo$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Intent $intent;
    int label;
    final /* synthetic */ AppCategoryIconProviderImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppCategoryIconProviderImpl$getPackageInfo$2(AppCategoryIconProviderImpl appCategoryIconProviderImpl, Intent intent, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appCategoryIconProviderImpl;
        this.$intent = intent;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppCategoryIconProviderImpl$getPackageInfo$2(this.this$0, this.$intent, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        PackageManagerWrapper packageManagerWrapper;
        ActivityInfo activityInfo;
        String str;
        Object packageInfo;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        packageManagerWrapper = this.this$0.packageManagerWrapper;
        Intent intent = this.$intent;
        packageManagerWrapper.getClass();
        ResolveInfo resolveActivity = PackageManagerWrapper.resolveActivity(intent);
        if (resolveActivity == null || (activityInfo = resolveActivity.activityInfo) == null || (str = activityInfo.packageName) == null) {
            return null;
        }
        AppCategoryIconProviderImpl appCategoryIconProviderImpl = this.this$0;
        this.label = 1;
        packageInfo = appCategoryIconProviderImpl.getPackageInfo(str, this);
        return packageInfo == coroutineSingletons ? coroutineSingletons : packageInfo;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((AppCategoryIconProviderImpl$getPackageInfo$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
