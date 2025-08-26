package com.android.systemui.dreams.homecontrols.system;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.dreams.homecontrols.HomeControlsDreamService;
import com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class HomeControlsDreamStartable implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope bgScope;
    public final ComponentName componentName;
    public final HomeControlsComponentInteractor homeControlsComponentInteractor;
    public final UserContextProvider userContextProvider;

    /* renamed from: com.android.systemui.dreams.homecontrols.system.HomeControlsDreamStartable$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return HomeControlsDreamStartable.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final HomeControlsDreamStartable homeControlsDreamStartable = HomeControlsDreamStartable.this;
                ReadonlyStateFlow readonlyStateFlow = homeControlsDreamStartable.homeControlsComponentInteractor.panelComponent;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.dreams.homecontrols.system.HomeControlsDreamStartable.start.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean z = ((ComponentName) obj2) != null;
                        int i2 = HomeControlsDreamStartable.$r8$clinit;
                        HomeControlsDreamStartable homeControlsDreamStartable2 = homeControlsDreamStartable;
                        homeControlsDreamStartable2.getClass();
                        ((UserTrackerImpl) homeControlsDreamStartable2.userContextProvider).getUserContext().getPackageManager().setComponentEnabledSetting(homeControlsDreamStartable2.componentName, z ? 1 : 2, 1);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    public HomeControlsDreamStartable(Context context, PackageManager packageManager, UserContextProvider userContextProvider, HomeControlsComponentInteractor homeControlsComponentInteractor, CoroutineScope coroutineScope) {
        this.userContextProvider = userContextProvider;
        this.homeControlsComponentInteractor = homeControlsComponentInteractor;
        this.bgScope = coroutineScope;
        this.componentName = new ComponentName(context, (Class<?>) HomeControlsDreamService.class);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new AnonymousClass1(null), 7);
    }
}
