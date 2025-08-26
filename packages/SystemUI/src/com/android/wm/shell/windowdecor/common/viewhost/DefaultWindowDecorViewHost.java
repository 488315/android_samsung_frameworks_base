package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Region;
import android.os.Trace;
import android.view.Display;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes3.dex */
public final class DefaultWindowDecorViewHost implements WindowDecorViewHost {
    public StandaloneCoroutine currentUpdateJob;
    public final CoroutineScope mainScope;
    public final SurfaceControlViewHostAdapter viewHostAdapter;

    /* renamed from: com.android.wm.shell.windowdecor.common.viewhost.DefaultWindowDecorViewHost$updateViewAsync$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ WindowManager.LayoutParams $attrs;
        final /* synthetic */ Configuration $configuration;
        final /* synthetic */ Region $touchableRegion;
        final /* synthetic */ View $view;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(View view, WindowManager.LayoutParams layoutParams, Configuration configuration, Region region, Continuation continuation) {
            super(2, continuation);
            this.$view = view;
            this.$attrs = layoutParams;
            this.$configuration = configuration;
            this.$touchableRegion = region;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DefaultWindowDecorViewHost.this.new AnonymousClass1(this.$view, this.$attrs, this.$configuration, this.$touchableRegion, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            DefaultWindowDecorViewHost defaultWindowDecorViewHost = DefaultWindowDecorViewHost.this;
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

    public DefaultWindowDecorViewHost(Context context, CoroutineScope coroutineScope, Display display, SurfaceControlViewHostAdapter surfaceControlViewHostAdapter) {
        this.mainScope = coroutineScope;
        this.viewHostAdapter = surfaceControlViewHostAdapter;
    }

    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost
    public final SurfaceControl getSurfaceControl() {
        return this.viewHostAdapter.rootSurface;
    }

    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost
    public final void release(SurfaceControl.Transaction transaction) {
        StandaloneCoroutine standaloneCoroutine = this.currentUpdateJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.currentUpdateJob = null;
        SurfaceControlViewHostAdapter surfaceControlViewHostAdapter = this.viewHostAdapter;
        SurfaceControlViewHost surfaceControlViewHost = surfaceControlViewHostAdapter.viewHost;
        if (surfaceControlViewHost != null) {
            surfaceControlViewHost.release();
        }
        transaction.remove(surfaceControlViewHostAdapter.rootSurface);
    }

    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost
    public final void updateView(View view, WindowManager.LayoutParams layoutParams, Configuration configuration, Region region, SurfaceControl.Transaction transaction) {
        Trace.beginSection("DefaultWindowDecorViewHost#updateView");
        StandaloneCoroutine standaloneCoroutine = this.currentUpdateJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.currentUpdateJob = null;
        Trace.beginSection("DefaultWindowDecorViewHost#updateViewHost");
        SurfaceControlViewHostAdapter surfaceControlViewHostAdapter = this.viewHostAdapter;
        surfaceControlViewHostAdapter.prepareViewHost(configuration, region);
        if (transaction != null) {
            surfaceControlViewHostAdapter.requireViewHost().getRootSurfaceControl().applyTransactionOnDraw(transaction);
        }
        surfaceControlViewHostAdapter.updateView(view, layoutParams);
        Trace.endSection();
        Trace.endSection();
    }

    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost
    public final void updateViewAsync(View view, WindowManager.LayoutParams layoutParams, Configuration configuration, Region region) {
        Trace.beginSection("DefaultWindowDecorViewHost#updateViewAsync");
        StandaloneCoroutine standaloneCoroutine = this.currentUpdateJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.currentUpdateJob = null;
        this.currentUpdateJob = BuildersKt.launch$default(this.mainScope, null, null, new AnonymousClass1(view, layoutParams, configuration, region, null), 3);
        Trace.endSection();
    }

    public /* synthetic */ DefaultWindowDecorViewHost(Context context, CoroutineScope coroutineScope, Display display, SurfaceControlViewHostAdapter surfaceControlViewHostAdapter, int i, DefaultConstructorMarker defaultConstructorMarker) {
        Context context2;
        Display display2;
        if ((i & 8) != 0) {
            context2 = context;
            display2 = display;
            surfaceControlViewHostAdapter = new SurfaceControlViewHostAdapter(context2, display2, null, 4, null);
        } else {
            context2 = context;
            display2 = display;
        }
        this(context2, coroutineScope, display2, surfaceControlViewHostAdapter);
    }
}
