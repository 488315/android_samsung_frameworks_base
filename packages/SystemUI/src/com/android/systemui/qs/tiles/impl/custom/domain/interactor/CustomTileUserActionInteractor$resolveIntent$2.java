package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class CustomTileUserActionInteractor$resolveIntent$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Intent $intent;
    final /* synthetic */ UserHandle $user;
    int label;
    final /* synthetic */ CustomTileUserActionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTileUserActionInteractor$resolveIntent$2(CustomTileUserActionInteractor customTileUserActionInteractor, Intent intent, UserHandle userHandle, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customTileUserActionInteractor;
        this.$intent = intent;
        this.$user = userHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomTileUserActionInteractor$resolveIntent$2(this.this$0, this.$intent, this.$user, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomTileUserActionInteractor$resolveIntent$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ResolveInfo resolveActivityAsUser = this.this$0.context.getPackageManager().resolveActivityAsUser(this.$intent, 0, this.$user.getIdentifier());
        ActivityInfo activityInfo = resolveActivityAsUser != null ? resolveActivityAsUser.activityInfo : null;
        if (activityInfo == null) {
            return null;
        }
        Intent intent = new Intent("android.service.quicksettings.action.QS_TILE_PREFERENCES");
        intent.setClassName(activityInfo.packageName, activityInfo.name);
        return intent;
    }
}
