package com.android.systemui.media.mediaoutput.controller.media;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

final class ColorSchemeLoader$process$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $callback;
    final /* synthetic */ Context $context;
    final /* synthetic */ String $packageName;
    int label;
    final /* synthetic */ ColorSchemeLoader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorSchemeLoader$process$3(Context context, ColorSchemeLoader colorSchemeLoader, Function2 function2, String str, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.this$0 = colorSchemeLoader;
        this.$callback = function2;
        this.$packageName = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ColorSchemeLoader$process$3(this.$context, this.this$0, this.$callback, this.$packageName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ColorSchemeLoader$process$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object failure;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PackageManager packageManager = this.$context.getPackageManager();
        String str = this.$packageName;
        try {
            int i = Result.$r8$clinit;
            failure = packageManager.getApplicationIcon(str);
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
        if (m2527exceptionOrNullimpl != null) {
            m2527exceptionOrNullimpl.printStackTrace();
        }
        if (failure instanceof Result.Failure) {
            failure = null;
        }
        Drawable drawable = (Drawable) failure;
        if (drawable == null) {
            return Unit.INSTANCE;
        }
        BuildersKt.launch$default(this.this$0.coroutineScope, null, null, new ColorSchemeLoader$process$4(drawable, this.$callback, null), 3);
        return Unit.INSTANCE;
    }
}
