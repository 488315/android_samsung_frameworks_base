package com.android.systemui.wallpapers.data.repository;

import com.android.systemui.user.data.model.SelectedUserModel;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class WallpaperRepositoryImpl$wallpaperInfo$3 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ WallpaperRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WallpaperRepositoryImpl$wallpaperInfo$3(WallpaperRepositoryImpl wallpaperRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = wallpaperRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WallpaperRepositoryImpl$wallpaperInfo$3 wallpaperRepositoryImpl$wallpaperInfo$3 = new WallpaperRepositoryImpl$wallpaperInfo$3(this.this$0, continuation);
        wallpaperRepositoryImpl$wallpaperInfo$3.L$0 = obj;
        return wallpaperRepositoryImpl$wallpaperInfo$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WallpaperRepositoryImpl$wallpaperInfo$3) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SelectedUserModel selectedUserModel = (SelectedUserModel) ((Pair) this.L$0).component2();
            WallpaperRepositoryImpl wallpaperRepositoryImpl = this.this$0;
            this.label = 1;
            wallpaperRepositoryImpl.getClass();
            obj = BuildersKt.withContext(wallpaperRepositoryImpl.bgDispatcher, new WallpaperRepositoryImpl$getWallpaper$2(wallpaperRepositoryImpl, selectedUserModel, null), this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}
