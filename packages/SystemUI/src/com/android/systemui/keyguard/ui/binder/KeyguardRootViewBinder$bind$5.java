package com.android.systemui.keyguard.ui.binder;

import android.app.WallpaperManager;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.wallpapers.data.repository.WallpaperFocalAreaRepositoryImpl;
import com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl;
import com.android.systemui.wallpapers.ui.viewmodel.WallpaperFocalAreaViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardRootViewBinder$bind$5 extends SuspendLambda implements Function3 {
    final /* synthetic */ WallpaperFocalAreaViewModel $wallpaperFocalAreaViewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$5$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ WallpaperFocalAreaViewModel $wallpaperFocalAreaViewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$5$1$1, reason: invalid class name and collision with other inner class name */
        final class C01981 extends SuspendLambda implements Function2 {
            final /* synthetic */ WallpaperFocalAreaViewModel $wallpaperFocalAreaViewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01981(WallpaperFocalAreaViewModel wallpaperFocalAreaViewModel, Continuation continuation) {
                super(2, continuation);
                this.$wallpaperFocalAreaViewModel = wallpaperFocalAreaViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01981(this.$wallpaperFocalAreaViewModel, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01981) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final WallpaperFocalAreaViewModel wallpaperFocalAreaViewModel = this.$wallpaperFocalAreaViewModel;
                    FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = wallpaperFocalAreaViewModel.wallpaperFocalAreaBounds;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder.bind.5.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            RectF rectF = (RectF) obj2;
                            WallpaperFocalAreaRepositoryImpl wallpaperFocalAreaRepositoryImpl = (WallpaperFocalAreaRepositoryImpl) WallpaperFocalAreaViewModel.this.wallpaperFocalAreaInteractor.wallpaperFocalAreaRepository;
                            wallpaperFocalAreaRepositoryImpl._wallpaperFocalAreaBounds.setValue(rectF);
                            WallpaperRepositoryImpl wallpaperRepositoryImpl = (WallpaperRepositoryImpl) wallpaperFocalAreaRepositoryImpl.wallpaperRepository;
                            if (WallpaperRepositoryImpl.DEBUG) {
                                wallpaperRepositoryImpl.getClass();
                                Log.d(WallpaperRepositoryImpl.TAG, "sendLockScreenLayoutChangeCommand " + rectF);
                            }
                            WallpaperManager wallpaperManager = wallpaperRepositoryImpl.wallpaperManager;
                            View view = wallpaperRepositoryImpl.rootView;
                            IBinder windowToken = view != null ? view.getWindowToken() : null;
                            Bundle bundle = new Bundle();
                            bundle.putFloat("wallpaperFocalAreaLeft", rectF.left);
                            bundle.putFloat("wallpaperFocalAreaRight", rectF.right);
                            bundle.putFloat("wallpaperFocalAreaTop", rectF.top);
                            bundle.putFloat("wallpaperFocalAreaBottom", rectF.bottom);
                            Unit unit = Unit.INSTANCE;
                            wallpaperManager.sendWallpaperCommand(windowToken, "android.wallpaper.lockscreen_layout_changed", 0, 0, 0, bundle);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(WallpaperFocalAreaViewModel wallpaperFocalAreaViewModel, Continuation continuation) {
            super(2, continuation);
            this.$wallpaperFocalAreaViewModel = wallpaperFocalAreaViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$wallpaperFocalAreaViewModel, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
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
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            if (((Boolean) this.$wallpaperFocalAreaViewModel.hasFocalArea.$$delegate_0.getValue()).booleanValue()) {
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01981(this.$wallpaperFocalAreaViewModel, null), 7);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRootViewBinder$bind$5(WallpaperFocalAreaViewModel wallpaperFocalAreaViewModel, Continuation continuation) {
        super(3, continuation);
        this.$wallpaperFocalAreaViewModel = wallpaperFocalAreaViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardRootViewBinder$bind$5 keyguardRootViewBinder$bind$5 = new KeyguardRootViewBinder$bind$5(this.$wallpaperFocalAreaViewModel, (Continuation) obj3);
        keyguardRootViewBinder$bind$5.L$0 = (LifecycleOwner) obj;
        return keyguardRootViewBinder$bind$5.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$wallpaperFocalAreaViewModel, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
