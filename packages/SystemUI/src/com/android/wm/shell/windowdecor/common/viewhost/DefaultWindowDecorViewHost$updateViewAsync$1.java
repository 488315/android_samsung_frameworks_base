package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.res.Configuration;
import android.graphics.Region;
import android.os.Trace;
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
final class DefaultWindowDecorViewHost$updateViewAsync$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ WindowManager.LayoutParams $attrs;
    final /* synthetic */ Configuration $configuration;
    final /* synthetic */ Region $touchableRegion;
    final /* synthetic */ View $view;
    int label;
    final /* synthetic */ DefaultWindowDecorViewHost this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultWindowDecorViewHost$updateViewAsync$1(DefaultWindowDecorViewHost defaultWindowDecorViewHost, View view, WindowManager.LayoutParams layoutParams, Configuration configuration, Region region, Continuation continuation) {
        super(2, continuation);
        this.this$0 = defaultWindowDecorViewHost;
        this.$view = view;
        this.$attrs = layoutParams;
        this.$configuration = configuration;
        this.$touchableRegion = region;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DefaultWindowDecorViewHost$updateViewAsync$1(this.this$0, this.$view, this.$attrs, this.$configuration, this.$touchableRegion, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DefaultWindowDecorViewHost$updateViewAsync$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DefaultWindowDecorViewHost defaultWindowDecorViewHost = this.this$0;
        View view = this.$view;
        WindowManager.LayoutParams layoutParams = this.$attrs;
        Configuration configuration = this.$configuration;
        Region region = this.$touchableRegion;
        defaultWindowDecorViewHost.getClass();
        Trace.beginSection("DefaultWindowDecorViewHost#updateViewHost");
        SurfaceControlViewHostAdapter surfaceControlViewHostAdapter = defaultWindowDecorViewHost.viewHostAdapter;
        surfaceControlViewHostAdapter.prepareViewHost(configuration, region);
        surfaceControlViewHostAdapter.updateView(view, layoutParams);
        Trace.endSection();
        return Unit.INSTANCE;
    }
}
