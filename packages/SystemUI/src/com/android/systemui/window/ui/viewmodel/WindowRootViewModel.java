package com.android.systemui.window.ui.viewmodel;

import android.os.Build;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.ui.transitions.GlanceableHubTransition;
import com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.window.domain.interactor.WindowRootViewBlurInteractor;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WindowRootViewModel {
    public static final Companion Companion = new Companion(null);
    public static final boolean isLoggable = Build.isDebuggable();
    public final ChannelLimitedFlowMerge _blurRadius;
    public final WindowRootViewBlurInteractor blurInteractor;
    public final ChannelFlowTransformLatest blurRadius;
    public final EmptyList bouncerBlurRadiusFlows;
    public final EmptyList glanceableHubBlurRadiusFlows;
    public final ChannelFlowTransformLatest isBlurOpaque;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 isPersistentEarlyWakeupRequired;
    public final KeyguardInteractor keyguardInteractor;
    public final ShadeInteractor shadeInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    public WindowRootViewModel(Set<PrimaryBouncerTransition> set, Set<GlanceableHubTransition> set2, WindowRootViewBlurInteractor windowRootViewBlurInteractor, KeyguardInteractor keyguardInteractor, ShadeInteractor shadeInteractor) {
        this.blurInteractor = windowRootViewBlurInteractor;
        this.keyguardInteractor = keyguardInteractor;
        this.shadeInteractor = shadeInteractor;
        EmptyList emptyList = EmptyList.INSTANCE;
        this.bouncerBlurRadiusFlows = emptyList;
        this.glanceableHubBlurRadiusFlows = emptyList;
        SpreadBuilder spreadBuilder = new SpreadBuilder(3);
        spreadBuilder.addSpread(emptyList.toArray(new Flow[0]));
        spreadBuilder.addSpread(emptyList.toArray(new Flow[0]));
        final ReadonlyStateFlow readonlyStateFlow = windowRootViewBlurInteractor.blurRadiusRequestedByShade;
        Flow flow = new Flow() { // from class: com.android.systemui.window.ui.viewmodel.WindowRootViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.window.ui.viewmodel.WindowRootViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.window.ui.viewmodel.WindowRootViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.window.ui.viewmodel.WindowRootViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.window.ui.viewmodel.WindowRootViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.window.ui.viewmodel.WindowRootViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.window.ui.viewmodel.WindowRootViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.window.ui.viewmodel.WindowRootViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        float r5 = (float) r5
                        java.lang.Float r6 = new java.lang.Float
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.window.ui.viewmodel.WindowRootViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Companion.getClass();
        spreadBuilder.add(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(flow, new WindowRootViewModel$Companion$logIfPossible$1("ShadeBlur", null)));
        this._blurRadius = FlowKt.merge(CollectionsKt__CollectionsKt.listOf(spreadBuilder.list.toArray(new Flow[spreadBuilder.list.size()])));
        WindowRootViewModel$special$$inlined$flatMapLatest$1 windowRootViewModel$special$$inlined$flatMapLatest$1 = new WindowRootViewModel$special$$inlined$flatMapLatest$1(null, this);
        ReadonlyStateFlow readonlyStateFlow2 = windowRootViewBlurInteractor.isBlurCurrentlySupported;
        this.blurRadius = FlowKt.transformLatest(readonlyStateFlow2, windowRootViewModel$special$$inlined$flatMapLatest$1);
        this.isPersistentEarlyWakeupRequired = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(FlowKt.transformLatest(readonlyStateFlow2, new WindowRootViewModel$special$$inlined$flatMapLatest$2(null, this))), new WindowRootViewModel$Companion$logIfPossible$1("isPersistentEarlyWakeupRequired", null));
        this.isBlurOpaque = FlowKt.transformLatest(readonlyStateFlow2, new WindowRootViewModel$special$$inlined$flatMapLatest$3(null, this));
    }
}
