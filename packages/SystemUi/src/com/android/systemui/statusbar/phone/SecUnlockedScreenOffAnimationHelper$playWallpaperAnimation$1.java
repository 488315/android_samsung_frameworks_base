package com.android.systemui.statusbar.phone;

import android.os.Bundle;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$playWallpaperAnimation$1", m277f = "SecUnlockedScreenOffAnimationHelper.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class SecUnlockedScreenOffAnimationHelper$playWallpaperAnimation$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SecUnlockedScreenOffAnimationHelper this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecUnlockedScreenOffAnimationHelper$playWallpaperAnimation$1(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, Continuation<? super SecUnlockedScreenOffAnimationHelper$playWallpaperAnimation$1> continuation) {
        super(2, continuation);
        this.this$0 = secUnlockedScreenOffAnimationHelper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecUnlockedScreenOffAnimationHelper$playWallpaperAnimation$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecUnlockedScreenOffAnimationHelper$playWallpaperAnimation$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.getClass();
        SecUnlockedScreenOffAnimationHelper.logD("playWallpaperAnimation");
        this.this$0.wallpaperManager.semSendWallpaperCommand(1, "samsung.android.wallpaper.goingtosleep", new Bundle());
        return Unit.INSTANCE;
    }
}
