package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Icon;
import android.os.UserHandle;
import com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class CustomTileDefaultsRepositoryImpl$loadDefaults$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ ComponentName $componentName;
    final /* synthetic */ UserHandle $user;
    int label;
    final /* synthetic */ CustomTileDefaultsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTileDefaultsRepositoryImpl$loadDefaults$2(CustomTileDefaultsRepositoryImpl customTileDefaultsRepositoryImpl, UserHandle userHandle, ComponentName componentName, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customTileDefaultsRepositoryImpl;
        this.$user = userHandle;
        this.$componentName = componentName;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomTileDefaultsRepositoryImpl$loadDefaults$2(this.this$0, this.$user, this.$componentName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomTileDefaultsRepositoryImpl$loadDefaults$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            Context createContextAsUser = this.this$0.context.createContextAsUser(this.$user, 0);
            CustomTileDefaultsRepositoryImpl customTileDefaultsRepositoryImpl = this.this$0;
            ComponentName componentName = this.$componentName;
            PackageManager packageManager = createContextAsUser.getPackageManager();
            customTileDefaultsRepositoryImpl.getClass();
            ServiceInfo serviceInfo = packageManager.getServiceInfo(componentName, packageManager.getApplicationInfo(componentName.getPackageName(), 0).isSystemApp() ? 786944 : 786432);
            int i = serviceInfo.icon;
            if (i == 0) {
                i = serviceInfo.applicationInfo.icon;
            }
            return i == 0 ? CustomTileDefaults.Error.INSTANCE : new CustomTileDefaults.Result(Icon.createWithResource(this.$componentName.getPackageName(), i), serviceInfo.loadLabel(createContextAsUser.getPackageManager()));
        } catch (PackageManager.NameNotFoundException unused) {
            return CustomTileDefaults.Error.INSTANCE;
        }
    }
}
