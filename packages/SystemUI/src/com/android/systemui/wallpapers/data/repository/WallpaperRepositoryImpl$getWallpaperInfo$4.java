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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class WallpaperRepositoryImpl$getWallpaperInfo$4 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $which;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ WallpaperRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WallpaperRepositoryImpl$getWallpaperInfo$4(WallpaperRepositoryImpl wallpaperRepositoryImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = wallpaperRepositoryImpl;
        this.$which = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WallpaperRepositoryImpl$getWallpaperInfo$4 wallpaperRepositoryImpl$getWallpaperInfo$4 = new WallpaperRepositoryImpl$getWallpaperInfo$4(this.this$0, this.$which, continuation);
        wallpaperRepositoryImpl$getWallpaperInfo$4.L$0 = obj;
        return wallpaperRepositoryImpl$getWallpaperInfo$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WallpaperRepositoryImpl$getWallpaperInfo$4) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
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
        SelectedUserModel selectedUserModel = (SelectedUserModel) ((Pair) this.L$0).component2();
        WallpaperRepositoryImpl wallpaperRepositoryImpl = this.this$0;
        int i2 = this.$which;
        this.label = 1;
        String str = WallpaperRepositoryImpl.TAG;
        wallpaperRepositoryImpl.getClass();
        Object withContext = BuildersKt.withContext(wallpaperRepositoryImpl.bgDispatcher, new WallpaperRepositoryImpl$getWallpaper$2(i2, wallpaperRepositoryImpl, selectedUserModel, null), this);
        return withContext == coroutineSingletons ? coroutineSingletons : withContext;
    }
}
