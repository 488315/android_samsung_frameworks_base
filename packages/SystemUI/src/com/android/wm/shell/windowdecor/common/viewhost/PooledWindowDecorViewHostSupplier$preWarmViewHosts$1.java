package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.Context;
import android.os.Trace;
import android.view.Display;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.WindowManager;
import android.widget.FrameLayout;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class PooledWindowDecorViewHostSupplier$preWarmViewHosts$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $preWarmSize;
    int label;
    final /* synthetic */ PooledWindowDecorViewHostSupplier this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PooledWindowDecorViewHostSupplier$preWarmViewHosts$1(int i, PooledWindowDecorViewHostSupplier pooledWindowDecorViewHostSupplier, Continuation continuation) {
        super(2, continuation);
        this.$preWarmSize = i;
        this.this$0 = pooledWindowDecorViewHostSupplier;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PooledWindowDecorViewHostSupplier$preWarmViewHosts$1(this.$preWarmSize, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PooledWindowDecorViewHostSupplier$preWarmViewHosts$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        int i = this.$preWarmSize;
        PooledWindowDecorViewHostSupplier pooledWindowDecorViewHostSupplier = this.this$0;
        for (int i2 = 0; i2 < i; i2++) {
            Context context = pooledWindowDecorViewHostSupplier.context;
            Display display = context.getDisplay();
            int i3 = pooledWindowDecorViewHostSupplier.nextDecorViewHostId;
            pooledWindowDecorViewHostSupplier.nextDecorViewHostId = i3 + 1;
            ReusableWindowDecorViewHost reusableWindowDecorViewHost = new ReusableWindowDecorViewHost(context, pooledWindowDecorViewHostSupplier.mainScope, display, i3, null, null, 0, 112, null);
            SurfaceControlViewHostAdapter surfaceControlViewHostAdapter = reusableWindowDecorViewHost.viewHostAdapter;
            SurfaceControlViewHost surfaceControlViewHost = surfaceControlViewHostAdapter.viewHost;
            if ((surfaceControlViewHost != null ? surfaceControlViewHost.getView() : null) == null) {
                Trace.beginSection("ReusableWindowDecorViewHost#warmUp");
                surfaceControlViewHostAdapter.prepareViewHost(reusableWindowDecorViewHost.context.getResources().getConfiguration(), null);
                FrameLayout frameLayout = reusableWindowDecorViewHost.rootView;
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(0, 0, 2, 8, -2);
                layoutParams.setTitle("View root of ReusableWindowDecorViewHost#" + reusableWindowDecorViewHost.id);
                layoutParams.setTrustedOverlay();
                Unit unit = Unit.INSTANCE;
                surfaceControlViewHostAdapter.updateView(frameLayout, layoutParams);
                Trace.endSection();
            }
            pooledWindowDecorViewHostSupplier.release(reusableWindowDecorViewHost, transaction);
        }
        return Unit.INSTANCE;
    }
}
