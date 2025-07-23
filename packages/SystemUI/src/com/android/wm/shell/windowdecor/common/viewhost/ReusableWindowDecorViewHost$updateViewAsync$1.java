package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.res.Configuration;
import android.graphics.Region;
import android.view.View;
import android.view.WindowManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ReusableWindowDecorViewHost$updateViewAsync$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ WindowManager.LayoutParams $attrs;
    final /* synthetic */ Configuration $configuration;
    final /* synthetic */ Region $touchableRegion;
    final /* synthetic */ View $view;
    int label;
    final /* synthetic */ ReusableWindowDecorViewHost this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReusableWindowDecorViewHost$updateViewAsync$1(ReusableWindowDecorViewHost reusableWindowDecorViewHost, View view, WindowManager.LayoutParams layoutParams, Configuration configuration, Region region, Continuation continuation) {
        super(2, continuation);
        this.this$0 = reusableWindowDecorViewHost;
        this.$view = view;
        this.$attrs = layoutParams;
        this.$configuration = configuration;
        this.$touchableRegion = region;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ReusableWindowDecorViewHost$updateViewAsync$1(this.this$0, this.$view, this.$attrs, this.$configuration, this.$touchableRegion, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ReusableWindowDecorViewHost$updateViewAsync$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ReusableWindowDecorViewHost reusableWindowDecorViewHost = this.this$0;
        View view = this.$view;
        WindowManager.LayoutParams layoutParams = this.$attrs;
        Configuration configuration = this.$configuration;
        Region region = this.$touchableRegion;
        int i = ReusableWindowDecorViewHost.$r8$clinit;
        reusableWindowDecorViewHost.updateViewHost$1(view, layoutParams, configuration, region, null);
        return Unit.INSTANCE;
    }
}
